package com.yuk.fuckMiui.hooks.modules.miuihome

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.yuk.fuckMiui.hooks.modules.BaseHook
import com.yuk.fuckMiui.utils.callMethodAs
import com.yuk.fuckMiui.utils.findClass
import com.yuk.fuckMiui.utils.getBoolean
import com.yuk.fuckMiui.utils.getObjectFieldAs
import com.yuk.fuckMiui.utils.hookAfterMethod

object CategoryFeatures : BaseHook() {
    override fun init() {

        if (getBoolean("miuihome_hide_allapps_category_all", false)) {
            try {
                findMethod("com.miui.home.launcher.allapps.category.BaseAllAppsCategoryListContainer") {
                    name == "buildSortCategoryList"
                }
            } catch (e: Exception) {
                findMethod("com.miui.home.launcher.allapps.category.AllAppsCategoryListContainer") {
                    name == "buildSortCategoryList"
                }
            }.hookAfter {
                val list = it.result as ArrayList<*>
                if (list.size > 1) {
                    list.removeAt(0)
                    it.result = list
                }
            }
        }

        if (getBoolean("miuihome_hide_allapps_category_paging_edit", false)) {
            "com.miui.home.launcher.allapps.AllAppsGridAdapter".hookAfterMethod(
                "onBindViewHolder", "com.miui.home.launcher.allapps.AllAppsGridAdapter.ViewHolder".findClass(), Int::class.javaPrimitiveType
            ) {
                if (it.args[0].callMethodAs<Int>("getItemViewType") == 64) {
                    it.args[0].getObjectFieldAs<View>("itemView").visibility = View.INVISIBLE
                }
            }
        }
    }

}