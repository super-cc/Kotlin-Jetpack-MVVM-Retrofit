# Kotlin+Jetpack+MVVM+Retrofit底层架构

- 语言主要使用Kotlin
- UI层主要使用Jetpack+MVVM
- 网络层使用OkHttp+Retrofit
- 数据库使用Room

点个star，谢谢

注意⚠：在使用混淆时，记得添加ViewBinding使用防混淆

```properties
-keep class * implements androidx.viewbinding.ViewBinding {
    *;
}
```


如果想用之前简单的MVVM架构，可以使用分支dev_mvvm（已不再维护更新）
