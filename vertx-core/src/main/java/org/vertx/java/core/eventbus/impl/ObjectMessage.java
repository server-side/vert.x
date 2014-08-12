/*
 * Copyright (c) 2011-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package org.vertx.java.core.eventbus.impl;

import io.netty.util.CharsetUtil;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Copyable;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.eventbus.MessageCodec;
import org.vertx.java.core.shareddata.Shareable;

import java.util.Map;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class ObjectMessage<T> extends BaseMessage<T> {

  public ObjectMessage(boolean send, String address, T object) {
	  super(send, address, object);
  }

  @Override
  protected byte type() {
    return MessageFactory.TYPE_OBJECT;
  }

  @Override
  protected Message<T> copy() {
    if (body instanceof Shareable) {
      return this;
    } else if (body instanceof Copyable) {
      Copyable copyable = (Copyable)body;
      Object copy = copyable.copy();
      if (copy == body) {
        throw new IllegalStateException("copy() must actually copy the object");
      }
      @SuppressWarnings("unchecked")
      Message<T> ret = new ObjectMessage(send, address, copy);
      return ret;
    } else {
      throw new IllegalArgumentException(body.getClass() + " does not implement Copyable or Shareable.");
    }
  }

  @Override
  protected void readBody(int pos, Buffer readBuff) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void writeBody(Buffer buff) {
	throw new UnsupportedOperationException();
  }

  @Override
  protected int getBodyLength() {
	throw new UnsupportedOperationException();
  }
}
