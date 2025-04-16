package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.EmptyFragmentBinding

class EmptyFragment : BaseFragment<EmptyFragmentBinding>() {

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): EmptyFragmentBinding {
        return EmptyFragmentBinding.inflate(inflater, container, false)
    }

    override fun initView() {
    }

    override fun setupObservers() {
    }

}