package background.work.around;

import android.print.PrinterId;
import android.printservice.PrintJob;
import android.printservice.PrinterDiscoverySession;
import java.util.List;

public class PrintService extends android.printservice.PrintService {

    /*
    This service is used only because on some Android versions, automatic suspension during the inactive period cannot be enabled for apps that have a print service.
    Этот сервис используется лишь потому, что на некоторых версиях Android автоматическая приостановка работы в неактивный период не включается для приложений имеющих сервис печати.
    */
    
    @Override
    protected void onConnected() {
        super.onConnected();
        DestroyPanic();
       }

     @Override
    public void onCreate() {
        super.onCreate();
        DestroyPanic();
    }

    public void DestroyPanic() {
        android.content.Intent intent = new android.content.Intent(getPackageName() + ".START");
        intent.setPackage(getPackageName()); 
        intent.addFlags(android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES);    
        sendBroadcast(intent);
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
        DestroyPanic();
    }

    @Override
    protected void onRequestCancelPrintJob(PrintJob printJob) {
        DestroyPanic();
    }
}
