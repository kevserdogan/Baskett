coucbase index oluşturma


 create index :CREATE INDEX `Product` ON `basketAppProduct`(`_class`)
    WHERE (`_class` = "com.basketapp.basketappproduct.entity.Product")
	burada get metotlarını çalıştırabiliriz. bu olmadan get metotları çalışmıyor. bu sorguyu couchbase arayüzünde query kısmında çalıştırabiliriz class bizim entity INDEX den sonra olan Product Entity class ismi ON dan sonra olan da bucket ismi
	
	user için index oluşşturuldu.
	CREATE INDEX `User` ON `basketAppUser`(`_class`) WHERE (`_class` = "com.basketapp.basketuser.basketappuser.entity.User")



docker kafka zookeper yml
version: "3"
services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092:9092'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper


bunu kaydettikten sonra komut satırında ilgili dosya pathine ulaştıktan sonra docker-compose -f name up




topic oluşturma 

docker-compose exec broker kafka-topics \ --create \  --bootstrap-server localhost:9092 \  --replication-factor 1 \  --partitions 1 \  --topic stockDecreased

docker-compose exec broker kafka-topics \ --create \  --bootstrap-server localhost:9092 \  --replication-factor 1 \  --partitions 1 \  --topic soldOut



