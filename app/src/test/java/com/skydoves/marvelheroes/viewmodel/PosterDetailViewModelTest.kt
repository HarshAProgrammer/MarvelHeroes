/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rackluxury.marvelheroes.viewmodel

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rackluxury.marvelheroes.persistence.PosterDao
import com.rackluxury.marvelheroes.repository.DetailRepository
import com.rackluxury.marvelheroes.utils.MockTestUtil.mockPoster
import com.rackluxury.marvelheroes.view.ui.details.PosterDetailViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.time.seconds

class PosterDetailViewModelTest {

  private lateinit var viewModel: PosterDetailViewModel
  private val posterDao: PosterDao = mock()
  private val repository: DetailRepository = DetailRepository(posterDao)

  @Before
  fun setup() {
    viewModel = PosterDetailViewModel(0, repository)
  }

  @Test
  fun getPosterTest() = runBlocking {
    val mockData = mockPoster()
    whenever(posterDao.getPoster(0)).thenReturn(mockPoster())

    repository.getPosterById(0).test(2.seconds) {
      val item = expectItem()
      Assert.assertEquals(item, mockData)
      expectComplete()
    }

    viewModel.posterFlow.test(2.seconds) {
      val item = expectItem()
      Assert.assertEquals(item, mockData)
      expectComplete()
    }
  }
}
