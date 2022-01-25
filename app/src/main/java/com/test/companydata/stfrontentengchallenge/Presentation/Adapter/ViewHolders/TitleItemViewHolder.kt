package com.test.companydata.stfrontentengchallenge.Presentation.Adapter.ViewHolders

import android.view.ViewGroup
import com.test.companydata.Core.base.BaseViewHolder
import com.test.companydata.stfrontentengchallenge.Domain.model.DashaboardItems
import com.test.companydata.stfrontentengchallenge.databinding.ItemTitleBinding

class TitleItemViewHolder (viewGroup: ViewGroup): BaseViewHolder <ItemTitleBinding> (viewGroup ,ItemTitleBinding::inflate ) {
    fun bindView( mTitle: DashaboardItems.Title){
        with(viewBinding){
            title.text = mTitle.headerTitle
        }
    }
}