# -*- coding: utf-8 -*-
from os.path import dirname, abspath, join

import torch
from huggingface_hub import hf_hub_url, cached_download
from omegaconf import OmegaConf

from .model import VQGanGumbelVAE


def get_vae(pretrained=True, cache_dir='/tmp/rudalle'):
    # TODO
    config = OmegaConf.load(join(dirname(abspath(__file__)), 'vqgan.gumbelf8-sber.config.yml'))
    vae = VQGanGumbelVAE(config)
    if pretrained:
        repo_id = 'shonenkov/rudalle-utils'
        filename = 'vqgan.gumbelf8-sber.model.ckpt'
        cache_dir = join(cache_dir, 'vae')
        config_file_url = hf_hub_url(repo_id=repo_id, filename=filename)
        cached_download(config_file_url, cache_dir=cache_dir, force_filename=filename)
        checkpoint = torch.load(join(cache_dir, filename), map_location='cpu')
        vae.model.load_state_dict(checkpoint['state_dict'], strict=False)
    print('vae --> ready')
    return vae
