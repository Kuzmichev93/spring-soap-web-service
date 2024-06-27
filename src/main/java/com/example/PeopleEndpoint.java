package com.example;


import com.data.Repository;

//import com.kafka.KafkaListenerExample;
import com.kafka.KafkaSender;
import com.kafka.User;
import jakarta.xml.soap.*;

import localhost._8080.*;

import localhost._8080.Error;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.http.MessageDispatcherServlet;



import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.HashMap;

@Endpoint
public class PeopleEndpoint extends MessageDispatcherServlet  {
	private static final String NAMESPACE_URI = "http://localhost:8080";
	private Repository model;
	public Information information;
	int value;
	KafkaSender kafkaSender;
	//KafkaListenerExample kafkaListenerExample;

	@Autowired

	public PeopleEndpoint(Repository model, Information information, KafkaSender kafkaSender
						  ) throws Exception {
		this.model=model;
		this.information=information;
		this.kafkaSender=kafkaSender;


	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
	@ResponsePayload
	public GetUserResponse getCountry(@RequestPayload GetUserRequest request) throws IOException, JSONException {
		GetUserResponse response = new GetUserResponse();
		Error er = new Error();
		er.setError("Пользователь не найден");
		if (model.getID(request.getSnils())!=0){

			response.setUser(information);
			return response;

		}
        response.setMessageError(er);

		return response;
	}

	@PayloadRoot(namespace =NAMESPACE_URI, localPart = "CreateUserRequest")
	@ResponsePayload
	public CreateUserResponse creatUser(@RequestPayload CreateUserRequest request,MessageContext messageContext) throws IOException, SOAPException {

		CreateUserResponse response = new CreateUserResponse();

		value = model.save(request.getName(),request.getSurname(),request.getCity(),request.getSnils());
		if(value!=0) {
			//Отправка сообщения в топик
			User user = new User();
			user.setName(request.getName());
			user.setSurname(request.getSurname());
			user.setCity(request.getCity());
			user.setSnils(request.getSnils());
			kafkaSender.sendMessage("mytopic",1,user,"test");

			response.setValue("Пользователь зарегистрирован");
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
	@ResponsePayload
	public UpdateUserResponse updatePeopleResponse(@RequestPayload UpdateUserRequest updatePeopleRequest){
		UpdateUserResponse update = new UpdateUserResponse();
        Error er = new Error();
        er.setError("Пользователя с таким снисл не существует");
		value = model.update(updatePeopleRequest.getName(),
				updatePeopleRequest.getSurname(),
				updatePeopleRequest.getCity(),
				updatePeopleRequest.getSnils());
		System.out.println(value);
        if(value!=0){

            update.setValue(information);
			return update;
		}
        update.setMessageError(er);

		return update;
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
	@ResponsePayload
	public DeleteUserResponse deletePeopleRequest(@RequestPayload DeleteUserRequest deletePeopleRequest){
		DeleteUserResponse deletePeopleResponse = new DeleteUserResponse();
        Error er = new Error();
        er.setError("Пользователя с таким снилс не существует");
		if(model.delete(deletePeopleRequest.getSnils())!=0){
			deletePeopleResponse.setValue("Пользователь удален");
			return deletePeopleResponse;
		}
		deletePeopleResponse.setMessageError(er);
		return deletePeopleResponse;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllPeopleRequest")
	@ResponsePayload
	public void getAllPeopleRequest(MessageContext messageContext) throws SOAPException {

		WebServiceMessage soapResponse =  messageContext.getResponse();
		SaajSoapMessage soap = (SaajSoapMessage)soapResponse;
		SOAPMessage soapMessage = soap.getSaajMessage();
		SOAPBodyElement stt = soapMessage.getSOAPBody().addBodyElement(new QName("ff","PeopleAll","ffh"));

		int valueall;
		HashMap<Integer, Information> response = model.getAllpeople();
		valueall = response.size();

		for(int k=0;k<valueall;k++){
			SOAPElement s = stt.addChildElement("People");
			s.addChildElement("Name").setTextContent(response.get(k).getName());
			s.addChildElement("Surname").setTextContent(response.get(k).getSurname());
			s.addChildElement("City").setTextContent(response.get(k).getCity());
			s.addChildElement("Snils").setTextContent(String.valueOf(response.get(k).getSnils()));
		}

	}

}
