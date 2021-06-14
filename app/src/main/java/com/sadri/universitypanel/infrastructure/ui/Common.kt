package com.sadri.universitypanel.infrastructure.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SnackBar(
  modifier: Modifier,
  text: String,
  dismiss: () -> Unit
) {
  Column {
    Snackbar(
      modifier = modifier.padding(8.dp),
      action = {
        Button(onClick = { dismiss() }) {
          Text("Hide")
        }
      },
    ) {
      Text(text = text)
    }
  }
}

@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier.fillMaxSize()
  ) {
    CircularProgressIndicator()
  }
}

@ExperimentalMaterialApi
@Composable
fun ProfileTopAppBarWithBottomSheet(
  username: String,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState
) {
  TopAppBar(
    title = {
      Text(text = username)
    },
    navigationIcon = {
      IconButton(onClick = {}) {
        Icon(Icons.Filled.AccountCircle, "")
      }
    },
    backgroundColor = MaterialTheme.colors.primary,
    contentColor = Color.White,
    elevation = 12.dp,
    actions = {
      IconButton(onClick = {
        coroutineScope.launch {
          bottomSheetScaffoldState.bottomSheetState.expand()
        }
      }) {
        Icon(Icons.Filled.Home, "")
      }
    }
  )
}

@ExperimentalMaterialApi
@Composable
fun ProfileTopAppBar(
  text: String,
  onBackClicked: () -> Unit
) {
  TopAppBar(
    title = {
      Text(text = text)
    },
    backgroundColor = MaterialTheme.colors.primary,
    contentColor = Color.White,
    elevation = 12.dp,
    actions = {
      IconButton(onClick = {
        onBackClicked()
      }) {
        Icon(Icons.Filled.ArrowBack, "")
      }
    }
  )
}

@ExperimentalMaterialApi
@Composable
fun LogoutBottomSheetContent(
  modifier: Modifier,
  coroutineScope: CoroutineScope,
  bottomSheetScaffoldState: BottomSheetScaffoldState,
  navigate: () -> Unit
) {
  Column(
    modifier = modifier
      .padding(16.dp)
      .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = " Logout from your account ? ",
      style = MaterialTheme.typography.h6
    )
    Row(modifier.padding(12.dp)) {
      Card(
        onClick = {
          coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
            navigate()
          }
        },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
          .padding(end = 20.dp),
        content = {
          Text(
            text = "Accept",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6,
            modifier = modifier.padding(8.dp)
          )
        }
      )
      Card(
        onClick = {
          coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
          }
        },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
          .padding(start = 20.dp),
        content = {
          Text(
            text = "Deny",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h6,
            modifier = modifier.padding(8.dp)
          )
        }
      )
    }
  }
}
