package com.quizz.database.services.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizz.database.beans.ResponseBean;
import com.quizz.database.beans.ResponseTmpBean;
import com.quizz.database.datas.ReturnCode;
import com.quizz.database.modeles.ResponseTmp;
import com.quizz.database.modeles.ReturnObject;
import com.quizz.database.repository.ResponseRepository;
import com.quizz.database.repository.ResponseTmpRepository;
import com.quizz.database.services.ResponseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {

	@Autowired
	private ResponseRepository responseRepository;

	@Autowired
	private ResponseTmpRepository responseTmpRepository;

	@Override
	public ReturnObject addTmpResponse(String key, String label, Boolean isValide) {
		log.info("Add responseTmp [key: " + key + "response: " + label + "]");
		ReturnObject object = new ReturnObject();

		try {
			ResponseTmpBean r = new ResponseTmpBean(key, label, isValide);
			// Save method was automatically managed by CrudRepository
			ResponseTmpBean responseTmpBean = responseTmpRepository.save(r);
			if (responseTmpBean != null) {
				object.setObject(getResponseTmptoResponseTmpBean(responseTmpBean));
				object.setCode(ReturnCode.ERROR_000);
				log.info("Response succesfully created");
			} else {
				object.setCode(ReturnCode.ERROR_050);
				log.error("Impossible to add ResponseTmp [key: " + key + ", responseTmp: [label:" + label + "]], " + ReturnCode.ERROR_050);
			}
		} catch (RuntimeException e) {
			object.setCode(ReturnCode.ERROR_200);
			log.error("idQuestion not exist in table Question [[response: " + label + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			object.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to add Response [response: " + label + "], " + ReturnCode.ERROR_050);
		}
		return object;
	}

	@Override
	public ReturnObject linkTmpResponse(int idQuestion, String pseudo) {
		ReturnObject obj = new ReturnObject();

		try {
			List<ResponseTmpBean> findByKey = responseTmpRepository.findByKeyContaining(pseudo);

			if(CollectionUtils.isNotEmpty(findByKey)){
				for (ResponseTmpBean responseTmpBean : findByKey) {
					ResponseBean responseBean = responseTmpBean.convertToResponseBean(idQuestion);
					responseRepository.save(responseBean);
					responseTmpRepository.delete(responseTmpBean);
				}
				obj.setCode(ReturnCode.ERROR_000);
			}else{
				log.error("There are not TmpResponse associated [pseudo: "+ pseudo + ", idQuestion: "+idQuestion + "]");
				obj.setCode(ReturnCode.ERROR_175);
			}
		} catch (IllegalArgumentException e) {
			obj.setCode(ReturnCode.ERROR_500);
			log.error("Impossible to delete ResponseTmpBean [id: " + idQuestion + "], " + ReturnCode.ERROR_500);
		} catch (RuntimeException e) {
			obj.setCode(ReturnCode.ERROR_200);
			log.error("idQuestion not exist in table Question [id: " + idQuestion + "], " + ReturnCode.ERROR_200);
		} catch (Exception e) {
			obj.setCode(ReturnCode.ERROR_050);
			log.error("Impossible to link Response [idQuestion: " + idQuestion + "], " + ReturnCode.ERROR_050);
		}
		return obj;
	}

	private ResponseTmp getResponseTmptoResponseTmpBean(ResponseTmpBean bean) {
		ResponseTmp ret = new ResponseTmp();
		if (bean != null) {
			ret.setId(bean.getId());
			ret.setIsValide(bean.getIsValide());
			ret.setKey(bean.getKey());
			ret.setLabel(bean.getLabel());
		}
		return ret;
	}
}
