package com.example.blackcardskmm.android.ui.views.lore

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.blackcardskmm.android.R
import com.example.blackcardskmm.android.ui.components.SearchTextField
import com.example.blackcardskmm.android.ui.theme.mikadanFont
import com.example.blackcardskmm.android.ui.views.fractions.FractionsSelectionBottomSheet
import com.example.blackcardskmm.android.util.startDownloadingFile
import com.example.blackcardskmm.components.lore.LoreComponent
import com.example.blackcardskmm.components.lore.LoreStore
import com.example.blackcardskmm.domain.models.LoreFile
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun Lore(
    component: LoreComponent
) {
    val state by component.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    fun closeFilters() {
        coroutineScope.launch {
            if (bottomSheetState.isVisible)
                bottomSheetState.hide()
            else
                bottomSheetState.show()
        }
    }

    val downloadFilesPermissionState = rememberMultiplePermissionsState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
        else {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    )

    LaunchedEffect(Unit) {
        downloadFilesPermissionState.launchMultiplePermissionRequest()
    }

    val onDownloadFileProcessing: (LoreFile, String) -> Unit =
        { file, msg ->
            file.apply {
                this.downloadedUri.value = null
            }
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(msg)
            }
        }

    val onDownloadFileFailed: (LoreFile, String) -> Unit =
        { file, errorMsg ->
            file.apply {
                this.downloadedUri.value = null
            }
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    errorMsg
                )
            }
        }

    val onDownloadFileSuccess: (LoreFile, String) -> Unit =
        { file, uri ->
            try {
                file.apply {
                    this.downloadedUri.value = uri
                }
                val intent = Intent(Intent.ACTION_VIEW)
                    .setData(uri.toUri())
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Не удалось открыть файл", Toast.LENGTH_LONG).show()
            }
        }

    val downloadFile: (LoreFile) -> Unit = remember {
        { file ->
            if (downloadFilesPermissionState.permissions.any { !it.status.isGranted }) {
                coroutineScope.launch {
                    downloadFilesPermissionState.launchMultiplePermissionRequest()
                }
            } else {
                startDownloadingFile(
                    context = context,
                    owner = owner,
                    file = file,
                    processing = { msg -> onDownloadFileProcessing(file, msg) },
                    success = { uri -> onDownloadFileSuccess(file, uri) },
                    failed = { errorMsg -> onDownloadFileFailed(file, errorMsg) }
                )
            }
        }
    }

    val openFile: (String?) -> Unit = remember {
        { uri ->
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                    .setData(uri?.toUri())
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Не удалось открыть файл", Toast.LENGTH_LONG).show()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            FractionsSelectionBottomSheet(
                fractions = state.fractions,
                onApplyFilters = remember {
                    {
                        component.onEvent(LoreStore.Intent.FiltersApplied)
                        closeFilters()
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.pergament),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
            )
            CollapsingToolbarScaffold(
                state = rememberCollapsingToolbarScaffoldState(),
                scrollStrategy = ScrollStrategy.EnterAlways,
                toolbar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { component.onOutput(LoreComponent.Output.NavigateBack) }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                        },
                        title = {
                            Text(
                                text = "Лор",
                                fontFamily = mikadanFont
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                (component::onEvent)(LoreStore.Intent.SearchActivated(!state.isSearchActive))
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                            IconButton(onClick = { closeFilters() }) {
                                Icon(
                                    imageVector = Icons.Filled.Settings,
                                    contentDescription = "Settings",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    )
                },
                modifier = Modifier
            ) {
                Column {
                    AnimatedVisibility(visible = state.isSearchActive) {
                        SearchTextField(
                            label = "Поиск по названию...",
                            textStyle = TextStyle(
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.onPrimary,
                                fontFamily = mikadanFont
                            ),
                            value = state.search,
                            onValueChange = remember { {
                                component.onEvent(LoreStore.Intent.SearchProcessing(it))
                            } },
                            onClear = remember { { component.onEvent(LoreStore.Intent.SearchCleared) } }
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(state.files.count(), key = { it }) { index ->
                            LoreFilesListItem(
                                file = state.files[index],
                                downloadFile = { downloadFile(state.files[index]) },
                                openFile = { openFile(it.downloadedUri.value) }
                            )
                        }
                    }
                }
            }
        }
    }
}