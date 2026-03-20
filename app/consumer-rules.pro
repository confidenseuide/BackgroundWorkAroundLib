-keep class background.work.around.** { *; }

-keep class **.RiderService { *; }
-keep class * extends **.RiderService { *; }

-keepattributes *Annotation*, Signature, InnerClasses
