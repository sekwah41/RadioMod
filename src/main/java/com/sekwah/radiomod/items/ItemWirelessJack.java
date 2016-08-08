package com.sekwah.radiomod.items;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.BlockSpeaker;
import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.blocks.tileentities.TileEntityAddon;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntitySpeaker;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;
import com.sekwah.radiomod.network.packets.client.ClientUpdateAddonUUIDPacket;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by on 06/08/2016.
 *
 * @author sekwah41
 */
public class ItemWirelessJack extends Item {

    public ItemWirelessJack() {
        this.setCreativeTab(CreativeTabRadio.creativeTabRadio);
    }

    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        if (!world.isRemote)
        {
            if (stack.getTagCompound() == null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                stack.setTagCompound(compound);
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return EnumActionResult.FAIL;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        //RadioMod.logger.info(tileEntity);
        NBTTagCompound nbt = stack.getTagCompound();

        if(tileEntity instanceof TileEntityAddon){
            TileEntityAddon speakerAddon = (TileEntityAddon) tileEntity;
            if(playerIn.isSneaking()){
                if(speakerAddon.getOwnerUUID() != null && !speakerAddon.getOwnerUUID().equals("")){
                    nbt.setString("UUID", speakerAddon.getOwnerUUID());
                    playerIn.addChatMessage(new TextComponentString("Grabbed UUID: " + speakerAddon.getOwnerUUID()));
                }
                else{

                }
            }
            else{
                if(!nbt.getString("UUID").equals("")){
                    playerIn.addChatMessage(new TextComponentString("Set UUID: " + nbt.getString("UUID")));
                    speakerAddon.setOwner(nbt.getString("UUID"));
                    speakerAddon.markDirty();
                    RadioMod.packetNetwork.sendToAll(new ClientUpdateAddonUUIDPacket(pos, nbt.getString("UUID")));
                }
                else{

                }
                return EnumActionResult.SUCCESS;
            }
            stack.setTagCompound(nbt);
            return EnumActionResult.SUCCESS;
        }
        else if(tileEntity instanceof TileEntityRadio){
            TileEntityRadio radioEntity = (TileEntityRadio) tileEntity;
            if(playerIn.isSneaking()){
                if(radioEntity.getUUID() != null && !radioEntity.getUUID().equals("")){
                    nbt.setString("UUID", radioEntity.getUUID());
                    playerIn.addChatMessage(new TextComponentString("Grabbed UUID: " + radioEntity.getUUID()));
                }
                else{
                    return EnumActionResult.FAIL;
                }
            }
            stack.setTagCompound(nbt);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

}
