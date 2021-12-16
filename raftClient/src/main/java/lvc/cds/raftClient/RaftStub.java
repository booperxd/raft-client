package lvc.cds.raftClient;

import java.util.concurrent.TimeUnit;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lvc.cds.raft.proto.ClientMessage;
import lvc.cds.raft.proto.RaftRPCGrpc;
import lvc.cds.raft.proto.RaftRPCGrpc.RaftRPCStub;

public class RaftStub {
    String peer;
    int port;

    ManagedChannel channel;
    RaftRPCStub stub;

    RaftStub(String peer, int port) {
        this.peer = peer;
        this.port = port;
        this.channel = null;
        this.stub = null;
        connect();
    }

    void connect() {
        channel = ManagedChannelBuilder.forAddress(peer, port).usePlaintext().build();
        stub = RaftRPCGrpc.newStub(channel);
    }

    RaftRPCStub getStub() {
        return stub;
    }

    void shutdown() throws InterruptedException {
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    void sendClientMessage(String log) {
        ClientMessage request = ClientMessage.newBuilder()
        .setLog(log)
        .build();

        getStub().clientRequest(request, new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {
                // we have the peer string (IP address) available here.
                System.out.println("received response from raft leader");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error contacting raft leader");
            }

            @Override
            public void onCompleted() {
                System.err.println("stream observer onCompleted");
            }
        });


    }
}
