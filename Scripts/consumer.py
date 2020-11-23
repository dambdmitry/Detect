import pika

from detector import detect
from files import removeFile
from producer import produce
from s3 import upload
credentials = pika.PlainCredentials(username='guest', password='guest')
consumerConnection = pika.BlockingConnection(pika.ConnectionParameters(host="localhost",
                                                                       port=5672,
                                                                       credentials=credentials
                                                                       ))
consumerChannel = consumerConnection.channel()

consumerChannel.queue_declare(queue="nondetected", durable=False)

def callback(ch, method, properties, body):
    print("callback run")
    print(str(body)[2:-1])
    path = detect(str(body)[2:-1])
    url = upload(path)
    removeFile(path)
    produce(url)
    print("callback done")

def consume():
    consumerChannel.basic_consume(queue='nondetected', on_message_callback=callback, auto_ack=True)
    consumerChannel.start_consuming()