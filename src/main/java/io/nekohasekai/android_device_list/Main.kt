package io.nekohasekai.android_device_list

import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import java.io.File

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // val devices = HttpUtil.downloadString("https://raw.githubusercontent.com/pbakondy/android-device-list/master/devices.json", CharsetUtil.CHARSET_UTF_8)

        val deviceList = JSONArray(File("devices.json").readText())
            .toList(JSONObject::class.java)

        val devices = HashSet<String>()

        devices.addAll(deviceList
            .filter { it.getStr("brand").isNullOrBlank() }
            .map { it.getStr("model") })

        for (device in deviceList
            .filter { !it.getStr("brand").isNullOrBlank() }
            .iterator()) {

            var brand = device.getStr("brand")
            if (brand.contains("(")) {
                brand = (brand.substringBefore("(") + brand.substringAfter(")", "")).trim()
            }
            val model = device.getStr("model")

            if (model.toLowerCase().contains(brand.toLowerCase())) {
                devices.add(model)
            } else {
                devices.add("$brand $model")
            }

        }

        File("devices.csv").writeText(devices.joinToString("\n"))

    }

}

