package com.unlp.pdtr.app;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: WebSocketService.proto")
public final class WebSocketServiceGrpc {

  private WebSocketServiceGrpc() {}

  public static final String SERVICE_NAME = "com.unlp.pdtr.app.WebSocketService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest,
      com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse> METHOD_SHOW_CONTENT =
      io.grpc.MethodDescriptor.<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest, com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.unlp.pdtr.app.WebSocketService", "showContent"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse.getDefaultInstance()))
          .setSchemaDescriptor(new WebSocketServiceMethodDescriptorSupplier("showContent"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WebSocketServiceStub newStub(io.grpc.Channel channel) {
    return new WebSocketServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WebSocketServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WebSocketServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WebSocketServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WebSocketServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class WebSocketServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest> showContent(
        io.grpc.stub.StreamObserver<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_SHOW_CONTENT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SHOW_CONTENT,
            asyncClientStreamingCall(
              new MethodHandlers<
                com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest,
                com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse>(
                  this, METHODID_SHOW_CONTENT)))
          .build();
    }
  }

  /**
   */
  public static final class WebSocketServiceStub extends io.grpc.stub.AbstractStub<WebSocketServiceStub> {
    private WebSocketServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WebSocketServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebSocketServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WebSocketServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketRequest> showContent(
        io.grpc.stub.StreamObserver<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_SHOW_CONTENT, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class WebSocketServiceBlockingStub extends io.grpc.stub.AbstractStub<WebSocketServiceBlockingStub> {
    private WebSocketServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WebSocketServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebSocketServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WebSocketServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class WebSocketServiceFutureStub extends io.grpc.stub.AbstractStub<WebSocketServiceFutureStub> {
    private WebSocketServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WebSocketServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebSocketServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WebSocketServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SHOW_CONTENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WebSocketServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WebSocketServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SHOW_CONTENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.showContent(
              (io.grpc.stub.StreamObserver<com.unlp.pdtr.app.WebSocketServiceOuterClass.WebSocketResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WebSocketServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WebSocketServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.unlp.pdtr.app.WebSocketServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WebSocketService");
    }
  }

  private static final class WebSocketServiceFileDescriptorSupplier
      extends WebSocketServiceBaseDescriptorSupplier {
    WebSocketServiceFileDescriptorSupplier() {}
  }

  private static final class WebSocketServiceMethodDescriptorSupplier
      extends WebSocketServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WebSocketServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WebSocketServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WebSocketServiceFileDescriptorSupplier())
              .addMethod(METHOD_SHOW_CONTENT)
              .build();
        }
      }
    }
    return result;
  }
}
