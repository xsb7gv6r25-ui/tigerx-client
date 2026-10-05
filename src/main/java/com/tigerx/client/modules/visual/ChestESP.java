package com.tigerx.client.modules.visual;

import com.tigerx.client.core.Module;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SmokerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class ChestESP extends Module {
    private double maxDistance = 128.0;

    public ChestESP() {
        super("ChestESP", "Muestra cofres a traves de paredes", Category.VISUAL, 67);
    }

    public double getMaxDistance() { return maxDistance; }
    public void setMaxDistance(double distance) { this.maxDistance = distance; }

    public List<BlockPos> scanChests(MinecraftClient client) {
        List<BlockPos> chestPositions = new ArrayList<>();
        if (!isEnabled()) return chestPositions;

        ClientWorld world = client.world;
        if (world == null || client.player == null) return chestPositions;

        BlockPos playerPos = client.player.getBlockPos();
        int radius = (int) maxDistance;
        int chunkRadius = radius >> 4;

        int playerChunkX = playerPos.getX() >> 4;
        int playerChunkZ = playerPos.getZ() >> 4;

        for (int cx = -chunkRadius; cx <= chunkRadius; cx++) {
            for (int cz = -chunkRadius; cz <= chunkRadius; cz++) {
                WorldChunk chunk = world.getChunk(playerChunkX + cx, playerChunkZ + cz);
                if (chunk == null) continue;

                for (BlockEntity be : chunk.getBlockEntities().values()) {
                    if (be instanceof ChestBlockEntity
                            || be instanceof BarrelBlockEntity
                            || be instanceof ShulkerBoxBlockEntity
                            || be instanceof HopperBlockEntity
                            || be instanceof FurnaceBlockEntity
                            || be instanceof BlastFurnaceBlockEntity
                            || be instanceof SmokerBlockEntity
                            || be instanceof DispenserBlockEntity
                            || be instanceof DropperBlockEntity) {

                        BlockPos pos = be.getPos();
                        double distance = Math.sqrt(pos.getSquaredDistance(playerPos));
                        if (distance <= maxDistance) {
                            chestPositions.add(pos);
                        }
                    }
                }
            }
        }
        return chestPositions;
    }

    public void render(MatrixStack matrices, VertexConsumerProvider consumers, Vec3d cameraPos, List<BlockPos> chests) {
        if (!isEnabled() || chests.isEmpty()) return;

        VertexConsumer buffer = consumers.getBuffer(RenderLayer.getLines());

        for (BlockPos pos : chests) {
            Box box = new Box(pos).expand(0.002);
            drawBox(matrices, buffer, box.offset(-cameraPos.x, -cameraPos.y, -cameraPos.z), 0.2f, 0.6f, 1.0f, 1.0f);
        }
    }

    private void drawBox(MatrixStack matrices, VertexConsumer buffer, Box box, float r, float g, float b, float a) {
        Matrix4f m = matrices.peek().getPositionMatrix();
        float x1 = (float) box.minX;
        float y1 = (float) box.minY;
        float z1 = (float) box.minZ;
        float x2 = (float) box.maxX;
        float y2 = (float) box.maxY;
        float z2 = (float) box.maxZ;

        buffer.vertex(m, x1, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z1).color(r, g, b, a).normal(0, 1, 0);
    }
}
