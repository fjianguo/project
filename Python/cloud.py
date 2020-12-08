#! /usr/bin/python3
 
# import
import jieba 
import matplotlib.pyplot as plt
from wordcloud import WordCloud
import numpy as np
from PIL import Image
    
mask = np.array(Image.open("weibo.png"))
jieba.load_userdict("name.txt")
text_from_file_with_apath =open("name.txt",encoding="UTF-8").read()
wordlist_after_jieba =jieba.cut(text_from_file_with_apath,cut_all=True)

wl_space_split =" ".join(wordlist_after_jieba)

my_wordcloud =WordCloud(font_path='./fonts/simhei.ttf',background_color="black", max_words=2000, mask=mask,scale=True)
my_wordcloud.generate(wl_space_split)
my_wordcloud.to_file("test_mask.png")
 
plt.imshow(my_wordcloud, interpolation='bilinear')
plt.axis("off")
plt.show()