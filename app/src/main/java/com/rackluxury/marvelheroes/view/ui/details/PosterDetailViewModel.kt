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

package com.rackluxury.marvelheroes.view.ui.details

import androidx.annotation.VisibleForTesting
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.rackluxury.marvelheroes.model.Poster
import com.rackluxury.marvelheroes.repository.DetailRepository
import kotlinx.coroutines.flow.Flow

class PosterDetailViewModel(
  posterId: Long,
  repository: DetailRepository
) : BindingViewModel() {

  @VisibleForTesting
  internal val posterFlow: Flow<Poster> = repository.getPosterById(posterId)

  @get:Bindable
  val poster: Poster? by posterFlow.asBindingProperty(viewModelScope, null)
}
