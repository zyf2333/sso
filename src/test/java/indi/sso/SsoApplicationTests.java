package indi.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SsoApplicationTests {

	@Test
	public void nioDemo() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(8080));
		ssc.register(selector, SelectionKey.OP_ACCEPT);//注册监听的事件
		while (true){
			Set selectedKeys = selector.selectedKeys();//取到所有的channel
			Iterator it = selectedKeys.iterator();
			while(it.hasNext()){//遍历
				SelectionKey key = (SelectionKey) it.next();
				if((key.readyOps()&SelectionKey.OP_ACCEPT)==SelectionKey.OP_ACCEPT){
					ServerSocketChannel sschannel = (ServerSocketChannel) key.channel();
					SocketChannel sc = sschannel.accept();//接收到服务端的请求
					sc.configureBlocking(false);
					ssc.register(selector, SelectionKey.OP_READ);
					it.remove();
				}else if((key.readyOps()&SelectionKey.OP_READ)==SelectionKey.OP_READ) {
					SocketChannel sc = (SocketChannel) key.channel();
					while (true) {
						buffer.clear();
						int n = sc.read(buffer);
						if (n < 0) {
							break;
						}
						buffer.flip();
					}
					it.remove();
				}
			}
		}


	}

	public static void main(String[] args) {
		String a = "a";
		String b = "a";
		System.out.println(a.equals(b));
		System.out.println(a==b);
	}

}
