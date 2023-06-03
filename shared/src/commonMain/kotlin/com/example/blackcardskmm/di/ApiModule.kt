import com.example.blackcardskmm.data.api.*
import org.koin.dsl.module

fun apiModule() = module {
    single { AuthApi(get()) }
    single { CardsApi(get()) }
    single { DecksApi(get()) }
    single { FractionsApi(get()) }
    single { FilesApi(get()) }
}