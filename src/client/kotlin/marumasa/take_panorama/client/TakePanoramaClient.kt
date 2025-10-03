package marumasa.take_panorama.client

import marumasa.take_panorama.TakePanorama
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.client.util.ScreenshotRecorder
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW


class TakePanoramaClient : ClientModInitializer {

    companion object {
        var isTakeing = false
        var counter = 0
        var g = 0F
        var l = 0
    }

    override fun onInitializeClient() {

        // キーバインド登録
        val takePanoramaKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
            createKeyBinding("take", GLFW.GLFW_KEY_F4)
        )

        ClientTickEvents.END_CLIENT_TICK.register({ client ->
            if (client.player == null) return@register
            // もしキーが押されたら
            if (takePanoramaKey.wasPressed()) {
                isTakeing = true
            }

            if (isTakeing) {

                when (counter) {
                    0 -> {
                        client.options.fov.value = 90
                        client.options.hudHidden = true
                        client.window.setWindowedSize(1024, 1024)
                        client.gameRenderer.setBlockOutlineEnabled(false)

                        client.player!!.yaw = g
                        client.player!!.pitch = 0.0f
                    }

                    1 -> {
                        l = 0
                        ScreenshotRecorder.saveScreenshot(
                            client.runDirectory, "panorama_$l.png", client.framebuffer
                        ) { _: Text? -> }
                        client.player!!.yaw = (g + 90.0f) % 360.0f
                        client.player!!.pitch = 0.0f
                    }

                    2 -> {
                        l = 1
                        ScreenshotRecorder.saveScreenshot(
                            client.runDirectory, "panorama_$l.png", client.framebuffer
                        ) { _: Text? -> }
                        client.player!!.yaw = (g + 180.0f) % 360.0f
                        client.player!!.pitch = 0.0f
                    }

                    3 -> {
                        l = 2
                        ScreenshotRecorder.saveScreenshot(
                            client.runDirectory, "panorama_$l.png", client.framebuffer
                        ) { _: Text? -> }
                        client.player!!.yaw = (g - 90.0f) % 360.0f
                        client.player!!.pitch = 0.0f
                    }

                    4 -> {
                        l = 3
                        ScreenshotRecorder.saveScreenshot(
                            client.runDirectory, "panorama_$l.png", client.framebuffer
                        ) { _: Text? -> }
                        client.player!!.yaw = g
                        client.player!!.pitch = -90.0f
                    }

                    5 -> {
                        l = 4
                        ScreenshotRecorder.saveScreenshot(
                            client.runDirectory, "panorama_$l.png", client.framebuffer
                        ) { _: Text? -> }
                        client.player!!.yaw = g
                        client.player!!.pitch = 90.0f
                    }

                    6 -> {
                        l = 5
                        ScreenshotRecorder.saveScreenshot(
                            client.runDirectory, "panorama_$l.png", client.framebuffer
                        ) { _: Text? -> }
                    }
                }
                counter++

                if (counter >= 6) {
                    counter = 0
                    isTakeing = false
                }
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
