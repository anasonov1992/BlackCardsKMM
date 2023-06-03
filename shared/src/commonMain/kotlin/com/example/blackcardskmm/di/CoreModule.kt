import com.example.blackcardskmm.util.CustomDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreModule(): Module = module {
    single {
        CustomDispatchers(
            default = Dispatchers.Default,
            main = Dispatchers.Main,
            io = Dispatchers.Default //FIXME
        )
    }
    single {
        CoroutineScope(Dispatchers.Default + SupervisorJob() )
    }
}