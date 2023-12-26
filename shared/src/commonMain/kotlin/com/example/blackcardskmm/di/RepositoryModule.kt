import com.example.blackcardskmm.data.repository.*
import com.example.blackcardskmm.domain.repository.*
import org.koin.dsl.module

fun repositoryModule() = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<FilesRepository> { FilesRepositoryImpl(get(), get()) }
    single<CardsRepository> { CardsRepositoryImpl(get(), get(), get()) }
    single<FractionsRepository> { FractionsRepositoryImpl(get(), get(), get()) }
    single<DecksRepository> { DecksRepositoryImpl(get(), get()) }
}