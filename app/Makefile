server:
	mvn -DskipTests package exec:java  -Dexec.mainClass=com.unlp.pdtr.app.App
client:
	mvn -DskipTests package exec:java  -Dexec.mainClass=com.unlp.pdtr.app.User -Dexec.cleanupDaemonThreads=false
#inciso_a_sv:
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.App -Dexec.args="A_SV"
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.Client -Dexec.args="A_SV" -Dexec.cleanupDaemonThreads=false || true
#inciso_a_acepto:
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.App -Dexec.args="A_ACEPTO"&
#	sleep 5 # para que se termine de ejecutar el servidor
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.Client -Dexec.args="A_ACEPTO" -Dexec.cleanupDaemonThreads=false || true
#inciso_b:
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.App -Dexec.args="B"&
#	sleep 5 # para que se termine de ejecutar el servidor
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.Client -Dexec.args="B" -Dexec.cleanupDaemonThreads=false || true
#	pkill -f Dexec.mainClass=pdytr.example.grpc.App 
#promedios:
#	chmod +x script_inciso_c/promedios.sh
#	./script_inciso_c/promedios.sh
#inciso_c:
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.App -Dexec.args="C"&
#	sleep 5 # para que se termine de ejecutar el servidor
#	mvn -DskipTests package exec:java  -Dexec.mainClass=pdytr.example.grpc.Client -Dexec.args="C $(promedio)" -Dexec.cleanupDaemonThreads=false || true
#	pkill -f Dexec.mainClass=pdytr.example.grpc.App 
