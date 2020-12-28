/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.fhir.datacapture.views

import android.view.View
import android.widget.CheckBox
import com.google.android.fhir.datacapture.R
import org.hl7.fhir.r4.model.BooleanType
import org.hl7.fhir.r4.model.QuestionnaireResponse

object QuestionnaireItemCheckBoxViewHolderFactory : QuestionnaireItemViewHolderFactory(
  R.layout.questionnaire_item_check_box_view
) {
  override fun getQuestionnaireItemViewHolderDelegate() =
    object : QuestionnaireItemViewHolderDelegate {
      private lateinit var checkBox: CheckBox
      private lateinit var questionnaireItemViewItem: QuestionnaireItemViewItem

      override fun init(itemView: View) {
        checkBox = itemView.findViewById(R.id.check_box)
        checkBox.setOnClickListener {
          questionnaireItemViewItem.questionnaireResponseItemComponent.answer = listOf(
            QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent().apply {
              value = BooleanType(checkBox.isChecked)
            }
          )
        }
      }

      override fun bind(questionnaireItemViewItem: QuestionnaireItemViewItem) {
        this.questionnaireItemViewItem = questionnaireItemViewItem
        checkBox.text = questionnaireItemViewItem.questionnaireItemComponent.text
        questionnaireItemViewItem.questionnaireResponseItemComponent.answer.also {
          if (it.size == 1 && it[0].hasValueBooleanType()) {
            checkBox.isChecked = it[0].valueBooleanType.value
          } else {
            checkBox.isChecked = false
          }
        }
      }
    }
}
