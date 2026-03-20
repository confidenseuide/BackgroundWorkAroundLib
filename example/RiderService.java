//EXAMPLE OF PATCH: java/your/pack/name/RiderService.java

/*
To start use:
background.work.around.Start.RunService(this);
or your context instead of "this".
*/

/*
ATTENTION: THIS IS A DEMONSTRATIONAL PROJECT. IF YOU DO NOT OVERRIDE ALL METHODS, THE SERVICE MAY PLAY SOUND TO DEMONSTRATE ITS WORK. IN THIS EXAMPLE ALL METHODS ARE ALREADY OVERRIDDEN.
ВНИМАНИЕ: ЭТО ДЕМОНСТРАЦИОННЫЙ ПРОЕКТ. ЕСЛИ ВЫ НЕ ПЕРЕОПРЕДЕЛИТЕ ВСЕ МЕТОДЫ, ОН МОЖЕТ ВОСПРОИЗВОДИТЬ ЗВУК ДЛЯ ДЕМОНСТРАЦИИ СВОЕЙ РАБОТЫ. В ЭТОМ ПРИМЕРЕ УЖЕ ВСЕ МЕТОДЫ ПЕРЕОПРЕДЕЛЕНЫ.
*/

package your.pack.name; //your package name. (Must match the path).

public class RiderService extends background.work.around.RiderService {

    @Override
    protected String NotificationTitle() { return "YourTitleName"; }

    @Override
    protected String NotificationBody() { return "YourBodyName"; }

    @Override
    protected void initLogicVoid() {
		/*
		This void runs right before the void responsible for startForeground. It's be useful if you need to do something before that. For example, requesting notification permissions. in other cases don't use it. And even if you do, don't put complicated logic in it.
		*/
	}

    @Override
    protected void serviceMainVoid() {
        //YOUR CORE LOGIC
        //This method is called when the service is first fully started (like OnCreate).
    }

    @Override
    protected void DestroyCleaner() {
        //cleaning resources when finished.
        //Don't call super.ondestroy here.
    }
}
