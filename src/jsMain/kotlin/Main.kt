import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

/**
 * Error:
 * java.lang.IllegalStateException: Symbol for /FunInterfaceWithComposable.content|2726578851058760068[0] is unbound
 * 	at org.jetbrains.kotlin.ir.symbols.impl.IrBindablePublicSymbolBase.getOwner(IrPublicSymbolBase.kt:52)
 * 	at org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater.collectImplementedSymbol(CacheUpdater.kt:299)
 * 	at org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater.getExportedSignaturesAndAddMetadata-gNUL48g(CacheUpdater.kt:351)
 * 	at org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater.rebuildDirtySourceMetadata(CacheUpdater.kt:403)
 * 	at org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater.actualizeCachesImpl(CacheUpdater.kt:704)
 * 	at org.jetbrains.kotlin.ir.backend.js.ic.CacheUpdater.actualizeCaches(CacheUpdater.kt:738)
 *
 * 	___
 *
 * 	Setting kotlin.incremental.js.ir=false makes it work
 */

// removing "fun" makes the ClassImplementingFunInterface work
fun interface FunInterfaceWithComposable {
    @Composable
    fun content()
}

// This class causes the problem even when it's not used.
class ClassImplementingFunInterface : FunInterfaceWithComposable {
    @Composable
    override fun content() {
        Text("ClassImplementingFunInterface")
    }
}


// This doesn't work :(
val funInterfaceWithComposableImpl = ClassImplementingFunInterface()

// This works fine:
//val funInterfaceWithComposableImpl = FunInterfaceWithComposable {
//    Text("funInterfaceWithComposableImpl")
//}

fun main() {
    renderComposable(rootElementId = "root") {
        funInterfaceWithComposableImpl.content()
    }
}

