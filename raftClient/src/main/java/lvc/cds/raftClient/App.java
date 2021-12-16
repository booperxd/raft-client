/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package lvc.cds.raftClient;

public class App {
    public static void main(String[] args) {
        String raftLeader = "1.1.1.1";
        int raftLeaderPort = 9876;

        RaftStub rStub = new RaftStub(raftLeader, raftLeaderPort);

        // present a menu. Allow user to send messages to leader. add a way to change
        // the leader, for testing.
        rStub.sendClientMessage("DEL Bubba");
    }
}