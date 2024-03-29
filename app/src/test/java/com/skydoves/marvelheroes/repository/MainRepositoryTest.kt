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

@file:Suppress("SpellCheckingInspection")

package com.rackluxury.marvelheroes.repository

import androidx.lifecycle.viewModelScope
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rackluxury.marvelheroes.MainCoroutinesRule
import com.rackluxury.marvelheroes.model.Poster
import com.rackluxury.marvelheroes.network.ApiUtil.getCall
import com.rackluxury.marvelheroes.network.MarvelClient
import com.rackluxury.marvelheroes.network.MarvelService
import com.rackluxury.marvelheroes.persistence.PosterDao
import com.rackluxury.marvelheroes.utils.MockTestUtil.mockPosterList
import com.rackluxury.marvelheroes.view.ui.main.MainViewModel
import com.skydoves.sandwich.ResponseDataSource
import com.skydoves.sandwich.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainRepositoryTest {

  private lateinit var viewModel: MainViewModel
  private lateinit var repository: MainRepository
  private lateinit var client: MarvelClient
  private val service: MarvelService = mock()
  private val posterDao: PosterDao = mock()
  private val dataSource: ResponseDataSource<List<Poster>> = ResponseDataSource()

  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    client = MarvelClient(service)
    repository = MainRepository(client, dataSource, posterDao)
    viewModel = MainViewModel(repository)
  }

  @Test
  fun loadMarvelPostersTest() = runBlocking {
    val mockData = mockPosterList()
    whenever(posterDao.getPosterList()).thenReturn(emptyList())
    whenever(service.fetchMarvelPosterList()).thenReturn(getCall(mockData))

    val compositeDisposable = CompositeDisposable()
    repository.loadMarvelPosters(
      compositeDisposable,
      viewModel.viewModelScope
    ) {
    }

    verify(posterDao, atLeastOnce()).getPosterList()
    verify(service, atLeastOnce()).fetchMarvelPosterList()
    Unit
  }
}
