# BackgroundWorkAroundLib

En: This is a library for the stable, continuous operation of background services. A service using this library will run continuously in the background, autostart on reboot and other system events, and restart on crashes. If your service is critical important, you can use this library to ensure its operation.

To use this library, you must import it by any means and create a RiderService file in your application project strictly at the path app/src/main/java/background/work/around/RiderService.java. For more information, see example/RiderService.java.

WARNING: DON'T USE application-id background.work.around. This is debug mode, plays sound.

TO IMPORT USE: 
```groovy
dependencies {  
implementation ("io.github.confidenseuide:BackgroundWorkAroundLib:11.0")  
}
```
in app/build.dradle

Warning: on low-performance devices it can overload the broadcast queue. This can prevent it or other applications from timely reacting to events.

Ru: Это библиотека для устойчивой постоянной работы фоновых сервисов. Сервис использующий эту библиотеку будет постоянно работать в фоне, автозапускаться при перезагрузке и множестве других системных событий, а также перезапускаться при падении. Если ваш сервис критически важен, вы можете использовать эту библиотку для обеспечения его работы.

Чтобы использовать эту библиотеку вы должны её импортироватть любым способом и создать файл RiderService в проекте приложения строго по пути app/src/main/java/background/work/around/RiderService.java. Подробнее в example/RiderService.java.

ВНИМАНИЕ: НЕ ИСПОЛЬЗУЙТЕ application-id background.work.around. Это режим отладки, воспроизводит звук.

ЧТОБЫ ИМПОРТИРОВАТЬ ИСПОЛЬЗУЙТЕ: 
```groovy
dependencies {  
implementation ("io.github.confidenseuide:BackgroundWorkAroundLib:11.0")  
}
```
внутри app/build.dradle

Предупреждение: на слабых устройствах может перегружать очередь broadcast. Это может помешать ему или другим приложениям своевременно реагировать на события.
