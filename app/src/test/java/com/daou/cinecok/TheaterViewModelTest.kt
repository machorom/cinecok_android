package com.daou.cinecok

import android.content.Context
import android.content.res.Resources
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daou.cinecok.data.repository.TheaterRepository
import com.daou.cinecok.data.repository.TheaterRepositoryImpl
import com.daou.cinecok.data.restapi.KSearchAPI
import com.daou.cinecok.ui.main.theater.TheaterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.manipulation.Ordering
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TheaterViewModelTest {
    // LiveData 테스팅
    // Components 작업을 동일한 Thread 에서 돌게 처리하도록 변경
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewModel: TheaterViewModel

    @Mock
    private lateinit var repository: TheaterRepositoryImpl
    @Mock
    lateinit var kApi : KSearchAPI
    @Mock
    lateinit var resource: Resources

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.viewModel = TheaterViewModel(repository)
    }

    @Test
    fun changeModeTest() {
        viewModel.crrViewType.value?.let {crrType->
            assertTrue(crrType == TheaterViewModel.VIEW_TYPE_LIST)
        }
        viewModel.onChangeMode()
        viewModel.crrViewType.value?.let {crrType->
            assertTrue(crrType == TheaterViewModel.VIEW_TYPE_MAP)
        }
    }

    @Test
    fun updateLocationTest() {
        viewModel.flagRefresh.value?.let {refresh->
            assertTrue(!refresh)
        }
        viewModel.onUpdateLoaction(true)
        viewModel.flagRefresh.value?.let {refresh->
            assertTrue(refresh)
        }
    }
}