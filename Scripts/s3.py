import boto3
from botocore.client import Config

from secret import YANDEX_ACCESS_KEY, YANDEX_SECRET_KEY, ENDPOINT
from files import *

s3 = boto3.resource(
    's3',
    aws_access_key_id=YANDEX_ACCESS_KEY,
    aws_secret_access_key=YANDEX_SECRET_KEY,
    config=Config(signature_version='s3v4'),
    endpoint_url=ENDPOINT
)
bucket = "detected-photos"


def upload(path):
    data = open(path, 'rb')
    key = generateFilename(path) + ".jpg"
    s3.Bucket(bucket).put_object(Key=key, Body=data)
    url = ENDPOINT + "/" + bucket + "/" + key
    return url
