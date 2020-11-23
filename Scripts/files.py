import hashlib
import os


def generateFilename(url):
    hash_object = hashlib.md5(url.encode())
    return hash_object.hexdigest()


def removeFile(path):
    os.remove(path)
