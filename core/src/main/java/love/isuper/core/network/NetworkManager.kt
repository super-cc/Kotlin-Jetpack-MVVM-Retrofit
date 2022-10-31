package love.isuper.core.network

import java.util.HashMap

/**
 * Created by shichao on 2022/7/22.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class NetworkManager private constructor() : INetwork{

    private var iNetwork: INetwork? = null

    fun init(network: INetwork) {
        this.iNetwork = network
    }

    override fun getBaseUrl(): String {
        return iNetwork?.getBaseUrl() ?: ""
    }

    override fun getHeaders(): HashMap<String, String> {
        return iNetwork?.getHeaders() ?: hashMapOf()
    }

    companion object {
        @Volatile
        private var instance: NetworkManager? = null

        fun getInstance(): NetworkManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = NetworkManager()
                    }
                }
            }
            return instance!!
        }
    }

}