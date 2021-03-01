package io.nekohasekai.android_device_list

import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import java.io.File

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // val devices = HttpUtil.downloadString("https://raw.githubusercontent.com/pbakondy/android-device-list/master/devices.json", CharsetUtil.CHARSET_UTF_8)

        val devices = HashSet<String>()

        for (device in JSONArray(File("devices.json").readText())
            .toList(JSONObject::class.java)) {

            var brand = device.getStr("brand")
            if (brand.contains("(")) {
                brand = (brand.substringBefore("(") + brand.substringAfter(")", "")).trim()
            }
            val model = device.getStr("model")
            val name = device.getStr("name")
            val deviceName = device.getStr("device")

            var deviceModel = brand

            deviceModel += when {
                name.isNotBlank() -> " $name"
                model.isNotBlank() -> " $model"
                else -> " $deviceName"
            }

            devices.add(deviceModel)

        }

        File("devices.csv").writeText(devices.joinToString("\n"))

    }

}

