import com.example.blackcardskmm.SharedPrefsAccessTokenStorage
import com.example.blackcardskmm.data.interfaces.AccessTokenStorage
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.android.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AccessTokenStorage> { SharedPrefsAccessTokenStorage(get()) }
    single { Android.create() }
}