package pl.morecraft.dev.morepianer.app.waiter;

@SuppressWarnings("WeakerAccess")
public final class WaiterControl {

    public static boolean IS_INITIALISED = false;

    public static int WAITER_SLEEP = 250;
    public static int WAITER_SECONDS = 15;
    public static int WAITER_TRYINGS = (WAITER_SECONDS * 1000) / WAITER_SLEEP;

    public static String IMG_LOADING = "/images/loading.gif";

    public static void closeWaiter() {
        IS_INITIALISED = true;
    }

}
