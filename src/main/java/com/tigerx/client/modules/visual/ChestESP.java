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
    private double maxDistance = 64.0;
    private final List<BlockPos> cachedChests = new ArrayList<>();
    private int tickCounter = 0;

    public ChestESP() {
        super("ChestESP", "Muestra cofres a traves de paredes", Category.VISUAL, 67);
    }

    public void onTick(MinecraftClient client) {
        if (!isEnabled()) {
            cachedChests.clear();
            tickCounter = 0;
            return;
        }

        tickCounter++;
        if (tickCounter < 20) return;
        tickCounter = 0;

        cachedChests.clear();

        ClientWorld world = client.world;
        if (world == null || client.player == null) return;

        BlockPos playerPos = client.player.getBlockPos();
        int chunkRadius = (int) ((maxDistance / 16.0) + 1.0);

        int playerChunkX = playerPos.getX() >> 4;
        int playerChunkZ = playerPos.getZ() >> 4;

        for (int cx = -chunkRadius; cx <= chunkRadius; cx++) {
            for (int cz = -chunkRadius; cz <= chunkRadius; cz++) {
                WorldChunk chunk = world.getChunk(playerChunkX + cx, playerChunkZ + cz);
                if (chunk == null) continue;

                for (BlockEntity be : chunk.getBlockEntities().values()) {
                    if (isContainer(be)) {
                        BlockPos pos = be.getPos();
                        if (playerPos.getSquaredDistance(pos) <= maxDistance * maxDistance) {
                            cachedChests.add(pos);
                        }
                    }
                }
            }
        }
    }

    private boolean isContainer(BlockEntity be) {
        return be instanceof ChestBlockEntity
                || be instanceof BarrelBlockEntity
                || be instanceof ShulkerBoxBlockEntity
                || be instanceof HopperBlockEntity
                || be instanceof FurnaceBlockEntity
                || be instanceof BlastFurnaceBlockEntity
                || be instanceof SmokerBlockEntity
                || be instanceof DispenserBlockEntity
                || be instanceof DropperBlockEntity;
    }

    public void render(MatrixStack matrices, VertexConsumerProvider consumers, Vec3d cameraPos) {
        if (!isEnabled() || cachedChests.isEmpty()) return;

        VertexConsumer buffer = consumers.getBuffer(RenderLayer.getLines());
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        for (BlockPos pos : cachedChests) {
            Box box = new Box(pos).expand(0.002).offset(-cameraPos.x, -cameraPos.y, -cameraPos.z);
            drawBox(matrix, buffer, box);
        }
    }

    private void drawBox(Matrix4f m, VertexConsumer buffer, Box box) {
        float x1 = (float) box.minX, y1 = (float) box.minY, z1 = (float) box.minZ;
        float x2 = (float) box.maxX, y2 = (float) box.maxY, z2 = (float) box.maxZ;
        float r = 0.2f, g = 0.6f, b = 1.0f, a = 1.0f;

        buffer.vertex(m, x1, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z1).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x1, y2, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y1, z2).color(r, g, b, a).normal(0, 1, 0);
        buffer.vertex(m, x2, y2, z2).color(r, g, b, a).normal(0, 1, 0);
    }
}
