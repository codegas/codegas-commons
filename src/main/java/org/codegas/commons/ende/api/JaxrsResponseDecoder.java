package org.codegas.commons.ende.api;

import javax.ws.rs.core.Response;

@FunctionalInterface
public interface JaxrsResponseDecoder<T> extends Decoder<Response, T> {

}
