import android.content.Context

object TokenManager {

    lateinit var context: Context
    var AccessToken = String()
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
        set(value) {
            field = value
            context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
                .edit().putString("accesstoken", value)!!.apply()
        }

    var RefreshToken = String()
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