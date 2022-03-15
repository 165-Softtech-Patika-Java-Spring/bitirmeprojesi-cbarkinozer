# Bitirme Projesi

Projenin Konusu:
Bir marketteki ürünlerin satış fiyatlarına göre son fiyatlarını belirleyen servisin Spring Boot Framework
kullanılarak yazılması ve isteğe bağlı olarak önyüzünün yazılması.

> **Gereksinimler:**

> **Backend:**

- Kullanıcıdan kullanıcı adı, şifre, isim, soy isim bilgilerini alarak sisteme kayıt yapılır.
- Sisteme kayıtlı kullanıcılar market ürünlerinin veri girişini yapabilir. (security package)
- Ürün türlerine göre KDV oranları değişiklik göstermektedir. Bu oranlar aşağıdaki tabloda (productType)
belirtilmiştir.  
- __**Zaman zaman KDV oranları değişiklik gösterebilmektedir.**__

![Image](https://www.linkpicture.com/q/Untitled_395.png)


- Ürün için veri girişi yapacak kullanıcı; ürünün adı, ürünün türü ve vergisiz satış fiyatı alanlarını
doldurur. Her bir ürün için KDV Tutarı ve ürünün son fiyatı da hesaplanarak sisteme kaydedilir. (taxFreePrice)
> **Kurallar ve gereksinimler:**
- Sisteme yeni kullanıcı tanımlanabilir, güncellenebilir ve silinebilir. (save update delete)
- Sisteme yeni ürünler tanımlanabilir, güncellenebilir ve silinebilir. (save update delete)
- Ürünlerin fiyatları güncellenebilir. (updatePrice())
- KDV oranları değiştiğinde sistemdeki ürünlere de bu değişiklik yansıtılmalıdır. Herhangi bir hata
durumunda tüm işlemler geri alınmalıdır. (transactional)
- Tüm ürünler listelenebilmelidir. (findAll)
- Ürün türlerine göre ürünler listelenebilmelidir. (findAllByProductType)
- Belirli bir fiyat aralığındaki ürünler listelenebilmelidir. (findByPriceInterval query)
- Ürün türlerine göre aşağıdaki gibi detay veri içeren bir bilgilendirme alınabilmelidir. (findDetailsByProductType query)

![Image](https://www.linkpicture.com/q/22_57.png)

> Validasyonlar: 
- Aynı kullanıcı adı ile kullanıcı tanımı yapılamaz.
- Kullanıcı girişi kullanıcı adı & şifre kombinasyonu ile yapılır.
- Ürün türü, fiyatı, adı gibi alanlar boş olamaz.
- Ürün fiyatı sıfır ya da negatif olamaz.
- KDV oranı negatif olamaz.
> Authentication:
- Güvenli endpointler kullanınız. (jwt, bearer vs. )
> Response:
- Başarılı ve başarısız responselar için modeller tanımlayın ve bunları kullanın.
> Dökümantasyon:
- Open API Specification (Swagger tercih sebebi)
> Exception Handling:
- Hatalı işlemler için doğru hata kodlarının dönüldüğünden emin olunuz.
> Test:
- Unit ve integration testleri yazınız. 

### Explanation of the Design Decisions
- Entity and controller design: [link]   
- VatRate is hold on a table because I want the API's users to set wanted VAT rates for wanted products without
changing the source code (for the other option creating an enum inside the Product table).  
- I implemented soft delete(cancel) for Users table because users often comeback to apps after deleting their accounts
and users data might be useful for data science(in the future).  
- I implemented hard delete for VAT table because it is very small and it's data is unimportant.  
- I implemented hard delete for Products because high product count expected (multiple products per user),
therefore I wanted to decrease the storage and query time costs.  
- I also added findAll and findById endpoints to the controllers even though they are not wanted,
because I felt the need while using the api.  