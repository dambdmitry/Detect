import pika

credentials = pika.PlainCredentials(username='guest', password='guest')

producerConnection = pika.BlockingConnection(
    pika.ConnectionParameters(host="localhost",
                              port=5672,
                              credentials=credentials))

producerChannel = producerConnection.channel()

producerChannel.queue_declare(queue="detected")


def produce(url):
    producerChannel.basic_publish(exchange="direct", routing_key="detected", body=url)
