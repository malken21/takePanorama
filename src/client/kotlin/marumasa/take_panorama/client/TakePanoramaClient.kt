package marumasa.take_panorama.client

import marumasa.take_panorama.TakePanorama
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW


class TakePanoramaClient : ClientModInitializer {

    override fun onInitializeClient() {

        // キーバインド登録
        val takePanoramaKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
            createKeyBinding("take", GLFW.GLFW_KEY_F4)
        )

        ClientTickEvents.END_CLIENT_TICK.register({ client ->
            if (client.player == null) return@register
            // もしキーが押されたら
            if (takePanoramaKey.wasPressed()) {
                MinecraftClient.getInstance().takePanorama(
                    MinecraftClient.getInstance().runDirectory,
                    2048,
                    2048
                )
            }
        })
    }

    // キーバインド 作成
    private fun createKeyBinding(name: String, code: Int): KeyBinding {
        return KeyBinding( // ID作成
            "key." + TakePanorama.MOD_ID + "." + name,  // どのキーか設定
            InputUtil.Type.KEYSYM, code,  // カテゴリ設定
            "key.categories." + TakePanorama.MOD_ID
        )
    }
}
