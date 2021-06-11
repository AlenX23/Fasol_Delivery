import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object TokenManager {

    lateinit var context: Context
    var AccessToken = String()
        @SuppressLint("StaticFieldLeak")
        get() {
            val tsLong = System.currentTimeMillis() / 1000
            val ts = tsLong.toString()

            if (CheckTokenToValid()) {
                return context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                    .getString("accesstoken", "")!!
            }
            return context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                .getString("accesstoken", "")!!

        }
        @SuppressLint("CommitPrefEdits")
        set(value) {
            field = value
            context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                .edit().putString("accesstoken", value)!!.apply()
        }

    var RefreshToken = String()
        @SuppressLint("StaticFieldLeak")
        get() {
            val tsLong = System.currentTimeMillis() / 1000
            val ts = tsLong.toString()

            if (CheckTokenToValid()) {
                return context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                    .getString("refreshtoken", "")!!
            }
            return context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                .getString("refreshtoken", "")!!

        }
        @SuppressLint("CommitPrefEdits")
        set(value) {
            field = value
            context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                .edit().putString("refreshtoken", value)!!.apply()
        }

    private fun updateToken() {

    }

    private fun CheckTokenToValid(): Boolean {
        return true
    }
}