import multiprocessing
import torch
from psutil import virtual_memory

ram_gb = round(virtual_memory().total / 1024**3, 1)

print('CPU:', multiprocessing.cpu_count())
print('RAM GB:', ram_gb)
print("PyTorch version:", torch.__version__)
print("CUDA version:", torch.version.cuda)
device = torch.device("cpu")
print("device:", device.type)

from rudalle.pipelines import generate_images, show
from rudalle import get_rudalle_model, get_tokenizer, get_vae, get_realesrgan, get_ruclip
from rudalle.utils import seed_everything

dalle = get_rudalle_model('Malevich', pretrained=True, fp16=False, device=device)

realesrgan = get_realesrgan('x4', device=device)
tokenizer = get_tokenizer()
vae = get_vae().to(device)
ruclip, ruclip_processor = get_ruclip('ruclip-vit-base-patch32-v5')
ruclip = ruclip.to(device)

text = 'пейзаж'
pil_images = []
scores = []

seed_everything(42)

for top_k, top_p, images_num in [
   (128, 0.9, 1)
]:
    _pil_images, _scores = generate_images(text, tokenizer, dalle, vae, top_k=top_k, images_num=images_num, top_p=top_p)
    pil_images += _pil_images
    scores += _scores
    
show([pil_image for pil_image, score in sorted(zip(pil_images, scores), key=lambda x: -x[1])] , 1)
    