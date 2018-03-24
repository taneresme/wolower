package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolower.ui.config.MasterpassConfig;

@Service
public class MasterpassService {
	@Autowired
	private MasterpassConfig masterpassConfig;

}
