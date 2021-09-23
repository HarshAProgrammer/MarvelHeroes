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

import android.content.Context
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import com.rackluxury.marvelheroes.R
import com.rackluxury.marvelheroes.databinding.ActivityPosterDetailBinding
import com.rackluxury.marvelheroes.extensions.onTransformationEndContainerApplyParams
import com.rackluxury.marvelheroes.model.Poster
import com.rackluxury.marvelheroes.view.adapter.PosterSeriesAdapter
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterDetailActivity :
  BindingActivity<ActivityPosterDetailBinding>(R.layout.activity_poster_detail) {

  private val posterId: Long by bundle(EXTRA_POSTER_ID, 0)
  private val viewModel: PosterDetailViewModel by viewModel { parametersOf(posterId) }

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationEndContainerApplyParams()
    super.onCreate(savedInstanceState)
    binding {
      dispatcher = this@PosterDetailActivity
      adapter = PosterSeriesAdapter(plot)
      vm = viewModel
    }
  }

  companion object {
    private const val EXTRA_POSTER_ID = "EXTRA_POSTER_ID"

    fun startActivity(
      context: Context,
      transformationLayout: TransformationLayout,
      poster: Poster
    ) = context.intentOf<PosterDetailActivity> {
      putExtra(EXTRA_POSTER_ID to poster.id)
      TransformationCompat.startActivity(transformationLayout, intent)
    }
  }
}
