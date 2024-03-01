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
    comments = "Source: DatabaseService.proto")
public final class DatabaseServiceGrpc {

  private DatabaseServiceGrpc() {}

  public static final String SERVICE_NAME = "com.unlp.pdtr.app.DatabaseService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest,
      com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse> METHOD_STORE_IN_DATABASE =
      io.grpc.MethodDescriptor.<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest, com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.unlp.pdtr.app.DatabaseService", "storeInDatabase"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse.getDefaultInstance()))
          .setSchemaDescriptor(new DatabaseServiceMethodDescriptorSupplier("storeInDatabase"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DatabaseServiceStub newStub(io.grpc.Channel channel) {
    return new DatabaseServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DatabaseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DatabaseServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DatabaseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DatabaseServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class DatabaseServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest> storeInDatabase(
        io.grpc.stub.StreamObserver<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_STORE_IN_DATABASE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_STORE_IN_DATABASE,
            asyncClientStreamingCall(
              new MethodHandlers<
                com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest,
                com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse>(
                  this, METHODID_STORE_IN_DATABASE)))
          .build();
    }
  }

  /**
   */
  public static final class DatabaseServiceStub extends io.grpc.stub.AbstractStub<DatabaseServiceStub> {
    private DatabaseServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DatabaseServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DatabaseServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DatabaseServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBRequest> storeInDatabase(
        io.grpc.stub.StreamObserver<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_STORE_IN_DATABASE, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class DatabaseServiceBlockingStub extends io.grpc.stub.AbstractStub<DatabaseServiceBlockingStub> {
    private DatabaseServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DatabaseServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DatabaseServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DatabaseServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class DatabaseServiceFutureStub extends io.grpc.stub.AbstractStub<DatabaseServiceFutureStub> {
    private DatabaseServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DatabaseServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DatabaseServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DatabaseServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_STORE_IN_DATABASE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DatabaseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DatabaseServiceImplBase serviceImpl, int methodId) {
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
        case METHODID_STORE_IN_DATABASE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.storeInDatabase(
              (io.grpc.stub.StreamObserver<com.unlp.pdtr.app.DatabaseServiceOuterClass.DBResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DatabaseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DatabaseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.unlp.pdtr.app.DatabaseServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DatabaseService");
    }
  }

  private static final class DatabaseServiceFileDescriptorSupplier
      extends DatabaseServiceBaseDescriptorSupplier {
    DatabaseServiceFileDescriptorSupplier() {}
  }

  private static final class DatabaseServiceMethodDescriptorSupplier
      extends DatabaseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DatabaseServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DatabaseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DatabaseServiceFileDescriptorSupplier())
              .addMethod(METHOD_STORE_IN_DATABASE)
              .build();
        }
      }
    }
    return result;
  }
}
