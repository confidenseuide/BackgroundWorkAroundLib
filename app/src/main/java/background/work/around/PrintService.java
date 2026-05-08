package background.work.around;

import android.print.PrinterId;
import android.printservice.PrintJob;
import android.printservice.PrinterDiscoverySession;
import java.util.List;

public final class PrintService extends android.printservice.PrintService {

    //THIS APP USES BACKGROUND.WORK.AROUND LIBRARY OR ITS FORK
    //about: https://github.com/confidenseuide/BackgroundWorkAroundLib/

    /*
    This service is exists only because on some Android versions, auto-suspension during the inactive period cannot be enabled for apps that have a print service. Disabled by default. Don't need enable it.    
    Этот сервис существует лишь потому, что на некоторых версиях Android автоматическая приостановка работы в неактивный период не включается для приложений имеющих сервис печати. Отключено по умолчанию. Не нужно включать его.
    */
    
    @Override
    protected void onConnected() {
        super.onConnected();
       }


    @Override
    protected PrinterDiscoverySession onCreatePrinterDiscoverySession() {
        return new PrinterDiscoverySession() {
            @Override public void onStartPrinterDiscovery(List<PrinterId> priorityList) {}
            @Override public void onStopPrinterDiscovery() {}
            @Override public void onValidatePrinters(List<PrinterId> printerIds) {}
            @Override public void onStartPrinterStateTracking(PrinterId printerId) {}
            @Override public void onStopPrinterStateTracking(PrinterId printerId) {}
            @Override public void onDestroy() {}
        };
    }

    @Override
    protected void onPrintJobQueued(PrintJob printJob) {
        
    }

    @Override
    protected void onRequestCancelPrintJob(PrintJob printJob) {
        
    }
}
