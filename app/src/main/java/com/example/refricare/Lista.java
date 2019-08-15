package com.example.refricare;

import java.io.Serializable;
import java.util.List;

public class Lista  implements Serializable {

        List<Refrigerador> refril;
        public Lista(List<Refrigerador> refril){
            this.refril=refril;
        }
        public Lista(){

        }

        public List<Refrigerador> getRefril() {
            return refril;
        }

        public void setRefril(List<Refrigerador> refril) {
            this.refril = refril;
        }

}
